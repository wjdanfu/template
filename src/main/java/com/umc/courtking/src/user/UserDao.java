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

    public int createUser(PostUserReq postuserReq){
        String createUserQuery = "insert into User (email,name, password) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{
                postuserReq.getEmail(), postuserReq.getName(), postuserReq.getPassword()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    public int checkName(String name){
        return this.jdbcTemplate.queryForObject("select exists(select name from User where name = ?)",
                int.class,
                name);
    }



    public int checkEmail(String email){
        return this.jdbcTemplate.queryForObject("select exists(select email from User where email = ?)",
                int.class,
                email);
    }

    public PostUserLoginPWRes checkAccount(String email){
        return this.jdbcTemplate.queryForObject("select email, password, userIdx from User where email=?",
                (rs, rowNum) -> new PostUserLoginPWRes(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("userIdx")),
                email);
    }
    public String checkLog(int userIdx){
        return this.jdbcTemplate.queryForObject("select status from Loghistory\n" +
                        "where loghistoryIdx=(select max(loghistoryIdx) from (select loghistoryIdx from loghistory where userIdx=?) a)",
                String.class,
                userIdx);
    }
    public int checkLogExist(int userIdx){
        return this.jdbcTemplate.queryForObject("select EXISTS(select loghistoryIdx from Loghistory where userIdx=?) as exist",
                int.class,
                userIdx);
    }
    public void recordLog(int userIdx,String status){
        this.jdbcTemplate.update("insert into Loghistory (userIdx,status) VALUES (?,?)",
                userIdx,status
        );
    }
    public char getAuto(int userIdx){
        return this.jdbcTemplate.queryForObject("select status from Loghistory where userIdx=? LIMIT 1",char.class,
                userIdx);
    }

    public List<GetSongRes> getSong(){
        return this.jdbcTemplate.query("select songIdx,A.albumIdx,S.name as singer,Song.title,coverImg from Song inner join Album A on Song.albumIdx = A.albumIdx\n" +
                        "inner join Singer S on A.singerIdx = S.singerIdx",
                (rs, rowNum) -> new GetSongRes(
                        rs.getInt("songIdx"),
                        rs.getInt("albumIdx"),
                        rs.getString("singer"),
                        rs.getString("title"),
                        rs.getString("coverImg"))
        );
    }
    public List<GetAlbumRes> getAlbum(){
        return this.jdbcTemplate.query("select albumIdx,title,name as singer,coverImg from Album inner join Singer S on Album.singerIdx = S.singerIdx",
                (rs, rowNum) -> new GetAlbumRes(
                        rs.getInt("albumIdx"),
                        rs.getString("title"),
                        rs.getString("singer"),
                        rs.getString("coverImg"))
        );
    }

    public List<GetAlbumSongRes> getAlbumSong(int albumIdx){
        return this.jdbcTemplate.query("select songIdx, Song.title, S.name as singer,isTitleSong\n" +
                        "from Song\n" +
                        "         inner join Album A on Song.albumIdx = A.albumIdx\n" +
                        "         inner join Singer S on A.singerIdx = S.singerIdx where A.albumIdx=?;",
                (rs, rowNum) -> new GetAlbumSongRes(
                        rs.getInt("songIdx"),
                        rs.getString("title"),
                        rs.getString("singer"),
                        rs.getString("isTitleSong")),
                albumIdx
        );
    }
}