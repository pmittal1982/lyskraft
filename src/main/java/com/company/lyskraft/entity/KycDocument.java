package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.KycDocumentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Audited
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KycDocument extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private KycDocumentType type;
    @Size(max= 100, message="KYC number should not be more than 100 characters")
    private String number;
    @NonNull
    private String imageUrl;
    @Transient
    private String imageName;

    public String getImageName() {
        if (imageUrl != null) {
            imageName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
        }
        return imageName;
    }
}
