package org.ss.services;

import org.ss.dao.PlaneDAO;
import org.ss.entity.Plane;

import java.util.List;

public class PlaneService {
    private final Plane plane = new Plane();
    private final PlaneDAO planeDAO = new PlaneDAO();

    private List<Plane> planeList;

    private PlaneService() {
        planeList = planeDAO.getPlaneList();
    }
}
