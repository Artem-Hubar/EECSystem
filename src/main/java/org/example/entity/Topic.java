package org.example.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Topic", schema = "public")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Topic_id_topic_seq")
    @SequenceGenerator(name = "Topic_id_topic_seq", sequenceName = "Topic_id_topic_seq", allocationSize = 1)
    @Column(name = "id_topic")
    private Integer idTopic;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "data", nullable = false)
    private Instant data;

    // Constructors
    public Topic() {}

    public Topic(String title, Instant data) {
        this.title = title;
        this.data = data;
    }

    // Getters and Setters
    public Integer getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Integer idTopic) {
        this.idTopic = idTopic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    // toString, equals, hashCode
    @Override
    public String toString() {
        return "Topic{" +
                "idTopic=" + idTopic +
                ", title='" + title + '\'' +
                ", data=" + data +
                '}';
    }
}
