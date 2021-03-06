package com.melons.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.melons.server.mapper.NationMapper;
import com.melons.server.pojo.Nation;
import com.melons.server.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglishen
 * @since 2020-11-14
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
