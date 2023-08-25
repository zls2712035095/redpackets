package com.zls.provider.mapper;

import com.zls.api.model.RedRobRecord;
import com.zls.api.model.RedRobRecordExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RedRobRecordMapper {
    @SelectProvider(type=RedRobRecordSqlProvider.class, method="countByExample")
    long countByExample(RedRobRecordExample example);

    @DeleteProvider(type=RedRobRecordSqlProvider.class, method="deleteByExample")
    int deleteByExample(RedRobRecordExample example);

    @Delete({
        "delete from red_rob_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into red_rob_record (id, user_id, ",
        "red_packet, amount, ",
        "rob_time, is_active)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{redPacket,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{robTime,jdbcType=TIMESTAMP}, #{isActive,jdbcType=TINYINT})"
    })
    int insert(RedRobRecord record);

    @InsertProvider(type=RedRobRecordSqlProvider.class, method="insertSelective")
    int insertSelective(RedRobRecord record);

    @SelectProvider(type=RedRobRecordSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="red_packet", property="redPacket", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="rob_time", property="robTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT)
    })
    List<RedRobRecord> selectByExample(RedRobRecordExample example);

    @Select({
        "select",
        "id, user_id, red_packet, amount, rob_time, is_active",
        "from red_rob_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="red_packet", property="redPacket", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="rob_time", property="robTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT)
    })
    RedRobRecord selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RedRobRecordSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RedRobRecord record, @Param("example") RedRobRecordExample example);

    @UpdateProvider(type=RedRobRecordSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RedRobRecord record, @Param("example") RedRobRecordExample example);

    @UpdateProvider(type=RedRobRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RedRobRecord record);

    @Update({
        "update red_rob_record",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "red_packet = #{redPacket,jdbcType=VARCHAR},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "rob_time = #{robTime,jdbcType=TIMESTAMP},",
          "is_active = #{isActive,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(RedRobRecord record);
}