package com.bimface.example.quick.dao.model;

import javax.persistence.*;

@Table(name = "example_quick_integrate_file")
public class ExampleQuickIntegrateFile {
    @Id
    private Long id;

    @Column(name = "integrate_id")
    private Long integrateId;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    private String specialty;

    @Column(name = "specialty_sort")
    private Float specialtySort;

    private String floor;

    @Column(name = "floor_sort")
    private Float floorSort;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return integrate_id
     */
    public Long getIntegrateId() {
        return integrateId;
    }

    /**
     * @param integrateId
     */
    public void setIntegrateId(Long integrateId) {
        this.integrateId = integrateId;
    }

    /**
     * @return file_id
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * @return file_name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * @param specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * @return specialty_sort
     */
    public Float getSpecialtySort() {
        return specialtySort;
    }

    /**
     * @param specialtySort
     */
    public void setSpecialtySort(Float specialtySort) {
        this.specialtySort = specialtySort;
    }

    /**
     * @return floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * @param floor
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * @return floor_sort
     */
    public Float getFloorSort() {
        return floorSort;
    }

    /**
     * @param floorSort
     */
    public void setFloorSort(Float floorSort) {
        this.floorSort = floorSort;
    }
}