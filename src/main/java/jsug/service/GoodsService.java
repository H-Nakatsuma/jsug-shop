package jsug.service;

import jsug.component.exception.GoodsNotFoundException;
import jsug.model.Goods;
import jsug.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GoodsService {
    @Autowired
    GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public Goods findOne(UUID goodsId) {
        return goodsRepository.findOne(goodsId)
                .orElseThrow(GoodsNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Goods> findByCategoryId(int categoryId, Pageable pageable) {
        return goodsRepository.findByCategoryId(categoryId, pageable);
    }
}
