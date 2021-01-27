CREATE DEFINER=`root`@`localhost` PROCEDURE `add_blog_of_like_by_blog_id`(IN `blogId` bigint)
BEGIN
	#Routine body goes here...
	DECLARE Numbers BIGINT;
	SELECT total_likes INTO Numbers FROM blog WHERE blog_id = blogId;
	SET Numbers = Numbers + 1;
	UPDATE blog SET total_likes = Numbers WHERE blog_id = blogId;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_total_views_by_blog_id`(IN `blogId` bigint)
BEGIN
	#Routine body goes here...
	DECLARE Numbers BIGINT;
	SELECT total_views INTO Numbers FROM blog WHERE blog_id = blogId;
	SET Numbers = Numbers + 1;
	UPDATE blog SET total_views = Numbers WHERE blog_id = blogId;
END