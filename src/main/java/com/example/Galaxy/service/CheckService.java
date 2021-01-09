package com.example.Galaxy.service;

public interface CheckService {
    boolean checkAccount(String account);

    boolean checkBlog(Long blogId);

    boolean checkComment(Long commentId);


}
