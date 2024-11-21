package net.skhu.bomin.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.skhu.bomin.domain.Post;

@Getter
@Builder
@AllArgsConstructor
public class PostDto {
    private String title;
    private String content;

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
