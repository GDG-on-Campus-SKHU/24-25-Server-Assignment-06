package net.skhu.bomin.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.skhu.bomin.domain.Post;

@Getter
@Builder
@AllArgsConstructor
public class PostInfoDto {
    private String title;
    private String content;

    public static PostInfoDto Postfrom(Post post){
        return PostInfoDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
