package com.ms.email.converters;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EmailConverter {

    private final ModelMapper modelMapper;

    public EmailConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmailDto emailModelToDto(EmailModel emailModel) {
        return modelMapper.map(emailModel, EmailDto.class);
    }

    public EmailModel emailDtoToModel(EmailDto emailDto) {
        return modelMapper.map(emailDto, EmailModel.class);
    }

    public Page<EmailDto> emailModelPageToDtoPage(Page<EmailModel> emailModelPage) {
        return emailModelPage.map(this::emailModelToDto);
    }

    public Page<EmailDto> emailDtoPageToModelPage(Page<EmailModel> emailModelPage) {
        return emailModelPage.map(this::emailModelToDto);
    }
}
