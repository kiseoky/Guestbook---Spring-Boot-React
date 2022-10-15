package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO guestbookDTO) {
        log.info("DTO ------------------");
        log.info(guestbookDTO);

        Guestbook guestbook = dtoToEntity(guestbookDTO);

        log.info(guestbook);

        guestbookRepository.save(guestbook);

        return guestbook.getId();
    }

    @Override
    public GuestbookDTO read(Long id) {
        Optional<Guestbook> guestbook = guestbookRepository.findById(id);

        return guestbook.isPresent() ? entityToDto(guestbook.get()) : null;
    }

    @Override
    public void remove(Long id) {
        guestbookRepository.deleteById(id);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> byId = guestbookRepository.findById(dto.getId());
        if (byId.isPresent()) {
            Guestbook entity = byId.get();
            entity.updateGuestbook(dto.getTitle(), dto.getContent());

            guestbookRepository.save(entity);
        }

    }

    public PageResultDTO<GuestbookDTO, Guestbook> list(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        BooleanBuilder search = getSearch(requestDTO);
        Page<Guestbook> result = guestbookRepository.findAll(search, pageable);
        Function<Guestbook, GuestbookDTO> guestbookEntityToDto = ((entity) ->
                entityToDto(entity)
        );
        return new PageResultDTO<>(result, guestbookEntityToDto);
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanExpression expression = qGuestbook.id.gt(0L);

        booleanBuilder.and(expression);

        if (type == null || keyword == null || keyword.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

}
