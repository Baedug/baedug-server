//package yerong.baedug.oauth;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import yerong.baedug.domain.member.Member;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Map;
//
//@Getter
//@Setter
//public class PrincipalDetails implements UserDetails, OAuth2User {
//    private Member member;
//    Map<String, Object> attributes;
//    public PrincipalDetails(Member member){
//        this.member = member;
//    }
//    public PrincipalDetails(Member member, Map<String, Object> attributes){
//
//        this.member = member;
//        this.attributes = attributes;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collector = new ArrayList<>();
//        collector.add(() -> member.getRole().getKey());
//        return collector;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return member.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public String getName() {
//        return (String) attributes.get("name");
//    }
//}