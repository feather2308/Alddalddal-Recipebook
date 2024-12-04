package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.entity.Cocktail;
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

    public List<Comments> getListCommentsByCocktail(Cocktail cocktail) {
        return commentsRepository.findByCocktail(cocktail);
    }

    public void createComments(Date time, String contents,
                               Account account, Cocktail cocktail) {
        commentsRepository.save(new Comments(time, contents, account, cocktail));
    }

    public void deleteCommentsById(int id) {
        commentsRepository.deleteById(id);
    }
}
