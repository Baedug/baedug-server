package yerong.baedug.note.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.directory.domain.Directory;
import yerong.baedug.note.domain.Note;
import yerong.baedug.note.dto.request.NoteSaveRequestDto;
import yerong.baedug.note.dto.request.UpdateNoteRequestDto;
import yerong.baedug.directory.repository.DirectoryRepository;
import yerong.baedug.member.repository.MemberRepository;
import yerong.baedug.note.repository.NoteRepository;
import yerong.baedug.note.service.NoteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;


    @Transactional
    @Override
    public Note save(NoteSaveRequestDto requestDto, Long directoryId){
        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 디렉토리 id 입니다."));

        Note note = Note.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .directory(directory)
                .build();

        directory.addNote(note);
        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public List<Note> findAll(Long directoryId){
        List<Note> notes = noteRepository.findAllByDirectoryId(directoryId);
        return notes;
    }

    @Override
    @Transactional
    public Note findById(Long noteId){
        return noteRepository.findById(noteId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 note id입니다."));
    }

    @Override
    @Transactional
    public void delete(Long noteId){
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 note id입니다."));
        authorizeNoteMember(note);
        note.removeDirectory();
        noteRepository.delete(note);
    }

    @Override
    @Transactional
    public Note update(Long id, UpdateNoteRequestDto requestDto){
        Note note = noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 note id 입니다."));
        String newTitle;
        String newContent;
        if(requestDto.getTitle() == null){
            newTitle = note.getTitle();
        }
        else {
            newTitle = requestDto.getTitle();
        }
        if(requestDto.getContent() == null){
            newContent = note.getContent();
        }
        else{
            newContent = requestDto.getContent();
        }
        authorizeNoteMember(note);
        note.update(newTitle, newContent);
        return note;
    }

    private static void authorizeNoteMember(Note note) {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!note.getDirectory().getMember().getSocialId().equals(socialId)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
