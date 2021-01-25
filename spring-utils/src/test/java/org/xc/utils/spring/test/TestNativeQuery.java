package org.xc.utils.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xc.utils.spring.data.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestNativeQuery {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testListResults() {
        String sql = "SELECT p_id AS \"pid\", name, id FROM tb_tree";
        QueryUtils queryUtils = new QueryUtils(entityManager);
        List<Tree> list = queryUtils.listResults(sql, Tree.class);
        System.out.println(list);
    }

    @Test
    public void testNativeQuery() {
        String sql = "SELECT p_id AS \"pid\" FROM tb_tree where name = ? AND p_id = ?";
        QueryUtils queryUtils = new QueryUtils(entityManager);
        Tree tree = queryUtils.singleResult(sql, Tree.class, "root", 0);
        System.out.println(tree);
    }

    @Test
    public void testPageRequest() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        String sql = "SELECT p_id AS \"pid\", name, id FROM tb_tree";
        QueryUtils queryUtils = new QueryUtils(entityManager);
        PageImpl<Tree> pageImpl = queryUtils.pageResults(sql, Tree.class, pageable);
        System.out.println(pageImpl.getTotalElements());
        System.out.println(pageImpl.getTotalPages());
        System.out.println(pageImpl.getContent());
    }

}
