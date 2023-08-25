package com.zls.provider.mapper;

import com.zls.api.model.RedDetail;
import com.zls.api.model.RedDetailExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RedDetailMapper {
    @SelectProvider(type=RedDetailSqlProvider.class, method="countByExample")
    long countByExample(RedDetailExample example);

    @DeleteProvider(type=RedDetailSqlProvider.class, method="deleteByExample")
    int deleteByExample(RedDetailExample example);

    @Delete({
        "delete from red_detail",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into red_detail (id, record_id, ",
        "amount, is_active, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{recordId,jdbcType=INTEGER}, ",
        "#{amount,jdbcType=DECIMAL}, #{isActive,jdbcType=TINYINT}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(RedDetail record);

    @InsertProvider(type=RedDetailSqlProvider.class, method="insertSelective")
    int insertSelective(RedDetail record);

    @SelectProvider(type=RedDetailSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="record_id", property="recordId", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<RedDetail> selectByExample(RedDetailExample example);

    @Select({
        "select",
        "id, record_id, amount, is_active, create_time",
        "from red_detail",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="record_id", property="recordId", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    RedDetail selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RedDetailSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RedDetail record, @Param("example") RedDetailExample example);

    @UpdateProvider(type=RedDetailSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RedDetail record, @Param("example") RedDetailExample example);

    @UpdateProvider(type=RedDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RedDetail record);

    @Update({
        "update red_detail",
        "set record_id = #{recordId,jdbcType=INTEGER},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "is_active = #{isActive,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(RedDetail record);
}