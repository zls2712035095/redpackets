package com.zls.provider.mapper;

import com.zls.api.model.RedRecord;
import com.zls.api.model.RedRecordExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RedRecordMapper {
    @SelectProvider(type=RedRecordSqlProvider.class, method="countByExample")
    long countByExample(RedRecordExample example);

    @DeleteProvider(type=RedRecordSqlProvider.class, method="deleteByExample")
    int deleteByExample(RedRecordExample example);

    @Delete({
        "delete from red_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into red_record (id, user_id, ",
        "red_packet, total, ",
        "amount, is_active, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{redPacket,jdbcType=VARCHAR}, #{total,jdbcType=INTEGER}, ",
        "#{amount,jdbcType=DECIMAL}, #{isActive,jdbcType=TINYINT}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(RedRecord record);

    @InsertProvider(type=RedRecordSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertSelective(RedRecord record);

    @SelectProvider(type=RedRecordSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="red_packet", property="redPacket", jdbcType=JdbcType.VARCHAR),
        @Result(column="total", property="total", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<RedRecord> selectByExample(RedRecordExample example);

    @Select({
        "select",
        "id, user_id, red_packet, total, amount, is_active, create_time",
        "from red_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="red_packet", property="redPacket", jdbcType=JdbcType.VARCHAR),
        @Result(column="total", property="total", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    RedRecord selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RedRecordSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RedRecord record, @Param("example") RedRecordExample example);

    @UpdateProvider(type=RedRecordSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RedRecord record, @Param("example") RedRecordExample example);

    @UpdateProvider(type=RedRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RedRecord record);

    @Update({
        "update red_record",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "red_packet = #{redPacket,jdbcType=VARCHAR},",
          "total = #{total,jdbcType=INTEGER},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "is_active = #{isActive,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(RedRecord record);
}