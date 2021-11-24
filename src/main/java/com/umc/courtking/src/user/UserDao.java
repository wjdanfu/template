package com.umc.courtking.src.user;

import com.umc.courtking.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> userRes(){
        return this.jdbcTemplate.query("Select * from users",
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("ID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email"))

        );
    }

    public int addUser(PostUserReq postuserReq){
        String createUserQuery = "insert into users (userName, ID, password, email) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postuserReq.getUserName(), postuserReq.getID(), postuserReq.getPassword(), postuserReq.getEmail()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }
}