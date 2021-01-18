package com.example.Galaxy.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.Comment;
import com.example.Galaxy.entity.CommentOfLike;
import com.example.Galaxy.service.CommentService;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.ExceptionEnums;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.util.enums.OperationType;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    private final static Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * showdoc
     * @param blogId 必选 Long  博客id
     * @return {"code":0,message:"请求成功",data:{}}
     * @catalog 博客
     * @title
     * @description 根据博客id获取所有评论
     * @method post
     * @url /blog/getAll
     */
    @LogAnnotation(description = "根据博客id获取所有评论",operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getAllComment(@RequestParam(name = "blogId", required = false, defaultValue = "1") Long blogId) {
        if (blogId == null) throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        return new Result(commentService.selectAll(0L, blogId));
    }

    /**
     * showdoc
     * @param blogId                必选 Long     博客id
     * @param userAvatar            必选 String   用户头像
     * @param parentId       必选 Long     父评论id
     * @param commentContent        必选 Long     评论内容
     * @return {"code":0,message:"请求成功",data:{}}
     * @catalog 博客评论
     * @title
     * @description 添加评论
     * @method post
     * @url /comment/add
     */
    @LogAnnotation(description = "添加评论",operationType = OperationType.INSERT)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addComment(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        Long blogId = params.getLong("blogId");
        String userAvatar = params.getString("userAvatar");
        Long parentId = params.getLong("commentId");
        String commentContent = params.getString("commentContent");
        if (blogId == null || userId == null || userAvatar == null || parentId == null || commentContent == null)
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        Comment comments = new Comment();
        comments.setBlogId(blogId);
        comments.setUserId(userId);
        comments.setParentId(parentId);
        comments.setCommentContent(commentContent);
        comments.setCreateTime(new Date());
        comments.setUpdateTime(new Date());
        commentService.insertSelective(comments);
        return Result.SUCCESS();
    }

    /**
     * showdoc
     * @param commentId          必选 Long  博客id
     * @param commentUserId      必选 Long  原评论userId
     * @param commentLikeAccount 必选Long   总点赞量
     * @return {"code":0,message:"请求成功",data:{}}
     * @catalog 博客评论
     * @title
     * @description 添加点赞
     * @method post
     * @url /comment/addLike
     */
    @LogAnnotation(description = "添加点赞",operationType = OperationType.INSERT)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/addLike", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addCommentLike(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        Comment comments = new Comment();
        CommentOfLike commentOfLike = new CommentOfLike();
        Long commentId = params.getLong("commentId");
        Long commentUserId = params.getLong("commentUserId");
        Long totalLikes = params.getLong("totalLikes");
        if (commentId == null || commentUserId == null || totalLikes == null)
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        comments.setCommentId(commentId);
        comments.setTotalLikes(totalLikes + 1);
        commentService.updateSelective(comments);

        commentOfLike.setUserId(commentUserId);
        commentOfLike.setCommentId(commentId);
        commentOfLike.setUserIdOfLike(userId);
        if (commentService.getCommentLike(commentOfLike) == null){//不存在则插入新数据
            commentService.insertSelective(commentOfLike);
        }else
            commentService.updateSelective(commentOfLike);
        return Result.SUCCESS();
    }


    /**
     * showdoc
     * @param commentId          必选 Long  博客id
     * @param commentUserId      必选 Long  原评论userId
     * @param commentLikeAccount 必选Long   总点赞量
     * @return {"code":0,message:"请求成功",data:{}}
     * @catalog 博客
     * @title
     * @description 删除点赞
     * @method post
     * @url /comment/deleteLike
     */
    @LogAnnotation(description = "删除点赞",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/deleteLike", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object deleteCommentLike(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        Long commentId = params.getLong("commentId");
        Long commentUserId = params.getLong("commentUserId");
        Long totalLikes = params.getLong("totalLikes");
        Comment comments = new Comment();
        CommentOfLike commentOfLike = new CommentOfLike();
        if (commentId == null || commentUserId == null || totalLikes == null)
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        comments.setCommentId(commentId);
        comments.setTotalLikes(totalLikes - 1);
        commentService.updateSelective(comments);
        commentOfLike.setUserId(commentUserId);
        commentOfLike.setCommentId(commentId);
        commentOfLike.setUserIdOfLike(userId);
        commentOfLike.setUpdateTime(new Date());
        commentOfLike.setIsDeleted(true);
        commentService.updateSelective(commentOfLike);
        return Result.SUCCESS();
    }

    /**
     * showdoc
     * @param commentId          必选 Long  博客id
     * @param commentUserId      必选 Long  原评论userId
     * @param commentLikeAccount 必选Long   总点赞量
     * @return {"code":0,message:"请求成功",data:{}}
     * @catalog 博客
     * @title
     * @description 获取未阅读的评论总数
     * @method get
     * @url /comment/unreadCommentAccount
     */
    @LogAnnotation(description = "获取未阅读的评论总数",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/unreadCommentAccount", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object getUnreadCommentAccount(HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        return new Result(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), commentService.selectUnread(JWTUtil.getUserId(token)));
    }

    @LogAnnotation(description = "获取评论总数",operationType = OperationType.SELECT)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/getCommentSum", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object commentSum(@RequestParam(name = "blogId", required = false, defaultValue = "-1") Long blogId) {
        return Result.SUCCESS(commentService.selectCommentSumByBlogId(blogId));
    }
}
