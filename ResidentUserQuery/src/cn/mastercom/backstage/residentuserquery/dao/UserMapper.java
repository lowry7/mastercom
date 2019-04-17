package cn.mastercom.backstage.residentuserquery.dao;


import cn.mastercom.backstage.residentuserquery.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from tb_ResidentUserQuery_User where user_id = #{id}")
    User selectByUserId(@Param("id") String id);

    int insert(User record);

    int insertSelective(User record);
}