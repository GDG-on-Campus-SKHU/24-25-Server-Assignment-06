package net.skhu.bomin.service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.skhu.bomin.domain.Post;
import net.skhu.bomin.dto.request.PostDto;
import net.skhu.bomin.dto.response.PostInfoDto;
import net.skhu.bomin.dto.response.PostList;
import net.skhu.bomin.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {
   private final PostRepository postRepository;

   //게시글 생성 C
   @Transactional
    public PostInfoDto createPost(PostDto postDto){
       Post post = postDto.toEntity();
       postRepository.save(post);

       return PostInfoDto.Postfrom(post);
   }

   //게시글 조회 R
   @Transactional(readOnly=true)
    public PostInfoDto findByPost(Long postId){
       Post post = postRepository.findById(postId)
               .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
       return PostInfoDto.Postfrom(post);
   }

   //게시글 수정 U
    @Transactional
    public PostInfoDto updateByPost(Long postId, PostDto postDto){
       Post post = postRepository.findById(postId)
               .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
       post.update(postDto.getTitle(), postDto.getContent());
       return PostInfoDto.Postfrom(post);
    }

    //게시글 삭제 D
    @Transactional
    public void deleteByPost(Long postId){
       postRepository.deleteById(postId);
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public PostList findAllPost(){
        List<Post> posts = postRepository.findAll();
        List<PostInfoDto> postInfoDTO = posts.stream().map(PostInfoDto::Postfrom).toList();
        return PostList.Postfrom(postInfoDTO);
    }
}
