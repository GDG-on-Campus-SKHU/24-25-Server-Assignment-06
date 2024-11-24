package net.skhu.bomin.dto.response;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PostList {
    List<PostInfoDto> postInfoDtos;
    public static PostList Postfrom(List<PostInfoDto> postInfoDTO){
        return PostList.builder()
                .postInfoDtos(postInfoDTO)
                .build();
    }
}