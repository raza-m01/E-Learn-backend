package com.elearn.app.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"categoryList","videos"})
@EqualsAndHashCode(exclude = {"categoryList","videos"})
public class Course {

    @Id
    private String courseId;

    @Column(unique = true ,nullable = false)
    @NonNull
    private String courseTitle;


    private String shortDesc;

    @Column(length = 2000) //by default 255
    private String longDesc;

    private Double coursePrice;

    @Column(name = "live")
    private boolean live=false;

    private double discount;

    private Date createdDate;

    // Store the image file path or URL
    private String courseBannerFilePath;

    //content type or banner file type
    private String bannerContentType;


//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[] image;

    //video-mapping Relation
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Video> videos =new ArrayList<>();       /*as this field is not owning by course so this field will not be persists in db when we persist the Course in the db.
                                                         but this is used to fetch the data from the db. means if we fetch the course from db then this field will also be retrieved from db. then this field will be useful to hold this data and send to service layer
                                                         not be used to persist data into db but to hold data which retrieved from db.*/

    //category-mapping relation
    @ManyToMany
    private List<Category> categoryList=new ArrayList<>();



    public void addCategory(Category category){
        categoryList.add(category);
        category.getCourses().add(this);
    }

    public void removeCategory(Category category){
        categoryList.remove(category);
        category.getCourses().remove(this);
    }

}
