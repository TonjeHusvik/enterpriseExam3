package com.example.enterpriseExam.model

import javax.persistence.*

@Entity
@Table(name = "authorities")
class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_authority_id_seq")
    @SequenceGenerator(
        name = "authorities_authority_id_seq",
        allocationSize = 1
    )

    @Column(name = "authority_id")
    val id: Long? = null,

    @Column(name = "authority_name")
    var authorityName: String
) {
    override fun toString(): String {
        return "AuthorityEntity(id=$id, authorityName='$authorityName')"
    }












}