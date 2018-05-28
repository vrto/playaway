package net.helpscout.playaway.company;

import net.helpscout.playaway.web.UrlParameterGuardian;
import net.helpscout.playaway.web.exception.MissingPaymentException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CompanyGuardian extends UrlParameterGuardian {

    @Override
    protected void checkUrlVariables(Map<String, Object> parameters) {
        Long companyId = (Long) parameters.get("companyId");
        if (companyId == 2) { // repository call or whatever
            throw new MissingPaymentException("You gotta pay buddy");
        }
    }
}
