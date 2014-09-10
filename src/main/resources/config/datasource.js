var ioc = {
	dataSource:{
		type:"com.alibaba.druid.pool.DruidDataSource",
		events:{
			depose:"close"
		},
		fields:{
			url:"jdbc:mysql://127.0.0.1:3306/weixin?useUnicode=true&characterEncoding=utf8",
			username:"root",
			password:"root",
			maxActive:20,
			testWhileIdle:true,
			validationQuery : "SELECT 'x'",
			removeAbandoned:true ,
			removeAbandonedTimeout:1800,
			filters:"stat"
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	}
};