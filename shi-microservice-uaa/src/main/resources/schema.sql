CREATE TABLE `uaa_user` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`username` VARCHAR(64) NOT NULL COMMENT '用户名',
	`password` VARCHAR(64) NOT NULL COMMENT '密码',
	`account_non_expired` BIT(1) NOT NULL COMMENT '用户未失效',
	`account_non_locked` BIT(1) NOT NULL COMMENT '用户未锁定',
	`credentials_non_expired` BIT(1) NOT NULL COMMENT '密码未失效',
	`enabled` BIT(1) NOT NULL COMMENT '是否启用',
	`created_date` DATETIME NULL DEFAULT NULL COMMENT '创建日期',
	`created_by` VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人',
	`last_modified_date` DATETIME NULL DEFAULT NULL COMMENT '修改日期',
	`last_modified_by` VARCHAR(64) NULL DEFAULT NULL COMMENT '修改人',
	`is_deleted` BIT(1) NOT NULL COMMENT '是否软删除',
	PRIMARY KEY (`id`)
)
COMMENT='用户表'
;

CREATE TABLE `uaa_authority` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`authority` VARCHAR(64) NOT NULL COMMENT '权限名称',
	`description` VARCHAR(64) NOT NULL COMMENT '权限描述',
	PRIMARY KEY (`id`)
)
COMMENT='权限表'
;

CREATE TABLE `uaa_role` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`authority` VARCHAR(64) NOT NULL COMMENT '角色名称',
	`description` VARCHAR(64) NOT NULL COMMENT '角色描述',
	PRIMARY KEY (`id`)
)
COMMENT='角色表'
;

CREATE TABLE `uaa_user_role_x` (
	`user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户 id',
	`role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色 id',
	PRIMARY KEY (`user_id`, `role_id`)
)
COMMENT='用户角色关联表'
;

CREATE TABLE `uaa_role_authority_x` (
	`role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色 id',
	`authority_id` BIGINT UNSIGNED NOT NULL COMMENT '权限 id',
	PRIMARY KEY (`role_id`, `authority_id`)
)
COMMENT='角色权限关联表'
;
