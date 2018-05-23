drop table if exists cv_request_row;


CREATE TABLE `request_set` (
  `cv_req_id` varchar(255) NOT NULL,
  `clien_ip` varchar(255) DEFAULT NULL,
  `cv_cookie_id` varchar(100) DEFAULT NULL,
  `cv_agent_id` varchar(20) DEFAULT NULL,
  `session_id` varchar(50) DEFAULT NULL,
  `uri` varchar(1000) DEFAULT NULL,
  `method` varchar(20) DEFAULT NULL,
  `cv_req_start_time` timestamp NULL DEFAULT NULL,
  `user_agent` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`cv_req_id`)
)


CREATE TABLE `response_set` (
  `cv_req_id` varchar(100) NOT NULL,
  `cv_cookie_id` varchar(100) DEFAULT NULL,
  `status` int(4) DEFAULT null,
  `cv_elapse_time` timestamp NULL DEFAULT NULL,
  `cv_res_end_time` timestamp NULL DEFAULT NULL,
  `user_agent` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`cv_req_id`)
)


/*INSERT INTO cv_req_id, cv_cookie_id, cv_agent_id, session_id, clien_ip, uri, method, cv_req_start_time, user_agent
request_set VALUES (#{cv_req_id.state}, #{cv_req_id.state},)*/

