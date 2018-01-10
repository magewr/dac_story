package com.example.wr.story.data.local.mapper;

import com.example.wr.story.data.local.dao.RealmString;
import com.example.wr.story.data.local.dao.StoryDAO;
import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.realm.RealmList;

/**
 * Created by WR on 2018-01-10.
 */

public class StoryMapper {
    public static List<StoryDTO> convertList(List<StoryDAO> daoList) {
        ArrayList<StoryDTO> dtoList = new ArrayList<>();
        for (StoryDAO dao : daoList)
            dtoList.add(convertItem(dao));

        return dtoList;
    }

    public static StoryDTO convertItem (StoryDAO dao) {
        StoryDTO dto = new StoryDTO();
        dto.setId(dao.getId());
        dto.setTitle(dao.getTitle());
        dto.setMemo(dao.getMemo());
        dto.setDate(dao.getDate());
        dto.setImagePathList(new ArrayList<>());
        for (RealmString string : dao.getImagePathList())
            dto.getImagePathList().add(string.getValue());
        return dto;
    }

    public static StoryDAO convertItem (StoryDTO dto) {
        StoryDAO dao = new StoryDAO();
        dao.setId(dto.getId());
        dao.setTitle(dto.getTitle());
        dao.setMemo(dto.getMemo());
        dao.setDate(dto.getDate());
        dao.setImagePathList(new RealmList<>());
        for (String string : dto.getImagePathList())
            dao.getImagePathList().add(new RealmString(string));
        return dao;
    }

    public static void copyItem (StoryDTO from, StoryDAO to) {
        to.setTitle(from.getTitle());
        to.setMemo(from.getMemo());
        to.setDate(from.getDate());
        to.setImagePathList(new RealmList<>());
        for (String string : from.getImagePathList())
            to.getImagePathList().add(new RealmString(string));
    }
}
