package net.skhu.bomin.repository;
import net.skhu.bomin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
