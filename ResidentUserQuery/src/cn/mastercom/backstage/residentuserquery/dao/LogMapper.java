package cn.mastercom.backstage.residentuserquery.dao;


import cn.mastercom.backstage.residentuserquery.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    int insert(Log record);

    int insertSelective(Log record);
}