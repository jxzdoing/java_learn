#数据库
create database if not exists em;

#用户表
drop table if exists em.user;
create table if not exists em.user(
   id int unsigned not null auto_increment comment '用户id',
   user_name varchar(30) not null comment '用户姓名',
   primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
comment='用户表';

#商品表
drop table if exists em.product;
create table if not exists em.product(
    id int unsigned not null auto_increment comment '商品id',
    product_name varchar(50) not null comment '商品名称',
    price double(10,2) not null comment '商品价格',
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
comment='商品表';

#订单表
drop table if exists em.order;
create table if not exists em.order(
    id int unsigned not null auto_incrementcomment '订单id',
    user_id int unsigned not null comment '用户id',
    product_id unsign int(11) not null comment '商品id',
    amount unsign int(10) not null comment '商品数量',
    total_price double(10,2) not null comment '订单金额',
    create_time datetime not null comment '订单创建时间',
    update_time datetime not null comment '订单更新时间',
    status tinyint(1) not null comment '订单状态：0-待支付，1-已支付，2-无效',
    primary key(id),
    foreign key (user_id) references user(id),
    foreign key (product_id) references order(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
comment='订单表';