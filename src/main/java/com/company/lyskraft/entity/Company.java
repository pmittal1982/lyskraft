package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.dto.ProfileCompletion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Audited
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Company extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @NotBlank
    @Size(max= 100, message="Company name should not be more than 100 characters")
    private String name;
    @Size(max= 100, message="Company Legal name should not be more than 100 characters")
    private String legalRepresentativeName;
    @Size(max= 50, message="Company number should not be more than 50 characters")
    private String companyNumber;
    @Size(max= 200, message="Address should not be more than 200 characters")
    private String address;
    @ManyToOne
    @NotAudited
    private Country country;
    private String pinCode;
    private String bankAccountNumber;
    @Size(max= 100, message="Bank name should not be more than 100 characters")
    private String bankName;
    private String swiftCode;
    @Email(message = "Email is Invalid")
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;
    @ManyToMany(fetch=FetchType.EAGER)
    @NotAudited
    private Set<Sku> sellInterest;
    @ManyToMany(fetch=FetchType.EAGER)
    @NotAudited
    private Set<Sku> buyInterest;
    private Locale locale;
    @OneToMany(fetch=FetchType.EAGER)
    @JsonIgnoreProperties({ "lastModifiedDate" })
    private Set<KycDocument> kycDocument;

    @Transient
    private ProfileCompletion profileCompletion;

    /**
     * This method helps provide recommendation for the next section to fill in profile.
     * @return
     */
    public ProfileCompletion getProfileCompletion() {
        this.profileCompletion = new ProfileCompletion();
        this.profileCompletion.setNext("Edit Profile");
        int completion = 50;

        // Order them is least priority first.
        if (this.kycDocument != null && !this.kycDocument.isEmpty()) {
            completion = completion + 35;
        } else {
            this.profileCompletion.setNext("Upload KYC documents");
        }
        if (this.bankAccountNumber != null && !this.bankAccountNumber.isBlank()) {
            completion = completion + 15;
        } else {
            this.profileCompletion.setNext("Add bank details");
        }
        this.profileCompletion.setCompletion(completion);
        return this.profileCompletion;
    }
}