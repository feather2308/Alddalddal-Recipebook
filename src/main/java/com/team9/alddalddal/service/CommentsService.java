package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.Comments;
import com.team9.alddalddal.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    public List<Comments> getListCommentsByName(String name) {
        return commentsRepository.findByName(name);
    }

    public void createComments(Date time, String contents,
                               String name, String uid) {
        commentsRepository.save(new Comments(time, contents, name, uid));
    }

    public void deleteCommentsById(int id) {
        commentsRepository.deleteById(id);
    }
}
