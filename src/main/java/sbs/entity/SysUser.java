package sbs.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    private Integer id;

    private String name;

    private String password;






}
