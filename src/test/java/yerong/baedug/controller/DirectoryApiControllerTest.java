package yerong.baedug.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import yerong.baedug.directory.domain.Directory;
import yerong.baedug.member.domain.Member;
import yerong.baedug.member.domain.Role;
import yerong.baedug.member.domain.SocialProvider;
import yerong.baedug.directory.dto.request.DirectorySaveRequestDto;
import yerong.baedug.directory.repository.DirectoryRepository;
import yerong.baedug.member.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DirectoryApiControllerTest {


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    public void mockSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        directoryRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @DisplayName("디렉토리 생성")
    @Test
    public void addDirectory() throws  Exception{

        //given
        final String url = "/api/directory";
        final String name = "first";

        final DirectorySaveRequestDto dto = new DirectorySaveRequestDto(name);

        final String requestBody = objectMapper.writeValueAsString(dto);

        Member member = Member.builder()
                .username("user1")
                .email("test1@naver.com")
                .socialProvider(SocialProvider.APPLE)
                .role(Role.USER)
                .socialId("1234567890")
                .build();
        Member savedMember = memberRepository.save(member);
        Long memberId = savedMember.getId();


        // 테스트에서 사용할 인증 토큰 생성
        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(member, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );
        //then
        resultActions
                .andExpect(status().isCreated());

        List<Directory> all = directoryRepository.findAll();

        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getMember().getId()).isEqualTo(memberId);

    }

}