package com.hesappcepte.auth;
import com.hesappcepte.auth.dto.AuthResponse;


import com.hesappcepte.areamember.AreaMember;
import com.hesappcepte.areamember.AreaMemberRepository;
import com.hesappcepte.areamember.AreaMemberRole;
import com.hesappcepte.auth.dto.LoginRequest;
import com.hesappcepte.auth.dto.RegisterRequest;
import com.hesappcepte.common.exception.BusinessException;
import com.hesappcepte.financearea.FinanceArea;
import com.hesappcepte.financearea.FinanceAreaRepository;
import com.hesappcepte.financearea.FinanceAreaType;
import com.hesappcepte.financearea.SharingType;
import com.hesappcepte.user.User;
import com.hesappcepte.user.UserRepository;
import com.hesappcepte.user.UserStatus;
import org.springframework.transaction.annotation.Transactional;
import com.hesappcepte.common.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final FinanceAreaRepository financeAreaRepository;
    private final AreaMemberRepository areaMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(
            UserRepository userRepository,
            FinanceAreaRepository financeAreaRepository,
            AreaMemberRepository areaMemberRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.financeAreaRepository = financeAreaRepository;
        this.areaMemberRepository = areaMemberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest request) {

        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);


        String normalizedDisplayName = request.displayName().trim();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String passwordHash = passwordEncoder.encode(request.password());

        User user = new User(
                normalizedDisplayName,
                normalizedEmail,
                passwordHash
        );
        userRepository.save(user);

        // Create a default personal finance area for every newly registered user.
        FinanceArea personalArea = new FinanceArea(
                "Kişisel",
                FinanceAreaType.PERSONAL,
                SharingType.PERSONAL,
                "TRY",
                user
        );

        financeAreaRepository.save(personalArea);

        AreaMember ownerMembership = new AreaMember(
                personalArea,
                user,
                AreaMemberRole.OWNER,
                user
        );
        areaMemberRepository.save(ownerMembership);

    }



    public AuthResponse login(LoginRequest request) {

        String normalizedEmail =
                request.email().trim().toLowerCase(Locale.ROOT);

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.INVALID_CREDENTIALS)
                );

        if (!passwordEncoder.matches(
                request.password(),   //Girilen parolanın veritabanında saklanan parola hash'iyle eşleşip eşleşmediğini kontrol eder.
                user.getPasswordHash()
        )) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        if (user.getStatus()!= UserStatus.ACTIVE){
            throw new BusinessException(ErrorCode.USER_DISABLED);
        }
        String accessToken = jwtService.generateToken(user);

        return new AuthResponse(accessToken);
        }

}


