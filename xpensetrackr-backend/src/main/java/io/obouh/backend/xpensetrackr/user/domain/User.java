package io.obouh.backend.xpensetrackr.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record User(@Id String id, String username, String password,String role){
}
