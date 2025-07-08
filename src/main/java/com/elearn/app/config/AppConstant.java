package com.elearn.app.config;

import java.io.File;

public class AppConstant {

    public static final String  DEFAULT_PAGE_NUMBER="0";

    public static final String DEFAULT_PAGE_SIZE="10";

    public static final String DEFAULT_SORT_BY="categoryTitle";

    public static  final String DEFAULT_SORT_DIR="asc";

    //courses

    public static final String DEFAULT_COURSE_PAGE_NUMBER="0";
    public static final String DEFAULT_COURSE_PAGE_SIZE="5";
    public static final String DEFAULT_COURSE_SORT_BY="createdDate";

    //path of the directory/folder where our banner image file is uploaded.
    public static final String  COURSE_BANNER_UPLOAD_DIR="uploads"+File.separator+"courses"+File.separator+"banners";
}
