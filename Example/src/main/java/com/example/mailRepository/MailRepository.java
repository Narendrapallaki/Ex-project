package com.example.mailRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.MailData;

@Repository
public interface MailRepository extends JpaRepository<MailData, Integer>{

}
