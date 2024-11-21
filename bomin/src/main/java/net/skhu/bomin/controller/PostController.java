package net.skhu.bomin.controller;
import lombok.RequiredArgsConstructor;
import net.skhu.bomin.dto.request.PostDto;
import net.skhu.bomin.dto.response.PostInfoDto;
import net.skhu.bomin.dto.response.PostList;
import net.skhu.bomin.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //restful웹 서비스 컨트롤러 정의할 때 사용
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping//C
    public ResponseEntity<PostInfoDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}") //저장
    public ResponseEntity<PostInfoDto> findByPost(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.findByPost(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")//수정
    public ResponseEntity<PostInfoDto> updateByPost(@PathVariable(name= "id") Long id,
                                                    @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updateByPost(id, postDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")//삭제
    public ResponseEntity<PostInfoDto> deleteByPost(@PathVariable(name="id") Long id){
        postService.deleteByPost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostList> findAllPost(){
        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
    }
}
