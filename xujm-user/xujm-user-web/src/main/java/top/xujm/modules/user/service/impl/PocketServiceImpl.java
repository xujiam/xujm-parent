package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.user.biz.UserResourceBiz;
import top.xujm.modules.user.common.UserRemindException;
import top.xujm.modules.user.common.UserResourceUtil;
import top.xujm.modules.user.common.UserResultEnum;
import top.xujm.modules.user.model.*;
import top.xujm.modules.user.repository.*;
import top.xujm.modules.user.service.PocketService;

import java.util.List;

/**
 * @author Xujm
 */
@Component
public class PocketServiceImpl extends UserBaseService implements PocketService {

    @Autowired
    private PocketRepository pocketRepository;
    @Autowired
    private ConsumeRepository consumeRepository;
    @Autowired
    private FrozenRepository frozenRepository;
    @Autowired
    private IncomeTypeRepository incomeTypeRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserResourceBiz userResourceBiz;

    @Override
    public UserPocket getUserPocket(){
        return getUserPocket(getLoginUserId());
    }

    public UserPocket getUserPocket(int userId){
        return pocketRepository.findFirstByUserId(userId);
    }

    @Override
    public double getTotalTicket(int userId){
        if(userId == 0){
            return 0;
        }
        return getUserPocket(userId).getTotalTicket();
    }

    @Override
    public double getTotalConsume(int userId){
        if(userId == 0){
            return 0;
        }
        return getUserPocket(userId).getTotalConsume();
    }

    //public double get


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserConsume consume(int userId, int toId, String consumeCode, int moduleId, int extendId, double costNum){
        UserConsumeType userConsumeType = getConsumeType(consumeCode);
        if(userConsumeType.getIsFrozen() == BooleanTypeEnum.TRUE.getType()){
            throw new UserRemindException(UserResultEnum.CONSUME_WAY_ERROR);
        }
        double surplusDiamond = verifySurplusDiamond(userId,costNum);
        double incomeNum = 0;
        if (costNum > 0) {
            int re = pocketRepository.deductDiamondByUserId(userId, costNum);
            if (re == 0) {
                throw new UserRemindException(UserResultEnum.NOT_MONEY);
            }
            //计算受益方佣金
            incomeNum = calculateIncome(costNum, userConsumeType.getTransRate(), userConsumeType.getRoundWay());
            if (incomeNum > 0 && toId > 0) {
                pocketRepository.increaseTicketByUserId(toId, incomeNum);
            }
        }
        UserConsume record = recordUserConsume(userId,costNum,extendId,incomeNum,moduleId,toId,consumeCode);
        record.setSurplusDiamond(surplusDiamond - costNum);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void income(int userId,String incomeCode,int moduleId,int extendId,double incomeNum){
        UserIncomeType userIncomeType = getIncomeType(incomeCode);
        if(userIncomeType != null){
            int re = pocketRepository.increaseDiamondByUserId(userId,incomeNum);
            if(re > 0){
                UserIncome userIncome = new UserIncome();
                userIncome.setUserId(userId);
                userIncome.setIncomeCode(incomeCode);
                userIncome.setModuleId(moduleId);
                userIncome.setExtendId(extendId);
                userIncome.setIncomeNum(incomeNum);
                userIncome.setAddTime(LibDateUtil.getNowTime());
                incomeRepository.save(userIncome);
            }
        }
    }

    /**
     * 冻结
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserFrozen frozen(int userId, int toId, String consumeCode, int moduleId, int extendId, double costNum){
        UserConsumeType userConsumeType = getConsumeType(consumeCode);
        if(userConsumeType.getIsFrozen() == BooleanTypeEnum.FALSE.getType()){
            throw new UserRemindException(UserResultEnum.CONSUME_WAY_ERROR);
        }
        double surplusDiamond = verifySurplusDiamond(userId,costNum);
        if(costNum > 0){
            int re = pocketRepository.frozenDiamondByUserId(userId, costNum);
            if (re == 0) {
                throw new UserRemindException(UserResultEnum.NOT_MONEY);
            }
        }
        UserFrozen record = new UserFrozen();
        record.setUserId(userId);
        record.setCostNum(costNum);
        record.setExtendId(extendId);
        record.setFrozenState((byte) 1);
        record.setModuleId(moduleId);
        record.setToId(toId);
        record.setConsumeCode(consumeCode);
        record.setAddTime(LibDateUtil.getNowTime());
        record = frozenRepository.save(record);
        record.setSurplusDiamond(surplusDiamond - costNum);
        return record;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void frozenConsume(int frozenId){
        frozenConsume(frozenId,0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void frozenConsume(int frozenId,int moduleId){
        UserFrozen userFrozen = frozenRepository.findFirstById(frozenId);
        if(userFrozen == null){
            throw new UserRemindException(UserResultEnum.FROZEN_ERROR);
        }
        if(userFrozen.getFrozenState() != 1){
            throw new UserRemindException(UserResultEnum.FROZEN_ERROR);
        }
        int re = frozenRepository.updateFrozenStateById(frozenId,(byte)2);
        if(re > 0){
            if(userFrozen.getCostNum() > 0){
                int r = pocketRepository.deductFrozenDiamondByUserId(userFrozen.getUserId(),userFrozen.getCostNum());
                if(r > 0){
                    UserConsumeType userConsumeType = getConsumeType(userFrozen.getConsumeCode());
                    //计算受益方佣金
                    double incomeNum = calculateIncome(userFrozen.getCostNum(), userConsumeType.getTransRate(), userConsumeType.getRoundWay());
                    if (incomeNum > 0 && userFrozen.getToId() > 0) {
                        pocketRepository.increaseTicketByUserId(userFrozen.getToId(), incomeNum);
                    }
                    if(moduleId == 0){
                        moduleId = userFrozen.getModuleId();
                    }
                    recordUserConsume(userFrozen.getUserId(),userFrozen.getCostNum(),userFrozen.getExtendId()
                            ,incomeNum,moduleId,userFrozen.getToId(),userFrozen.getConsumeCode());
                }
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void backFrozen(int frozenId){
        UserFrozen userFrozen = frozenRepository.findFirstById(frozenId);
        if(userFrozen == null){
            throw new UserRemindException(UserResultEnum.FROZEN_ERROR);
        }
        //表示已消费不返还
        if(userFrozen.getFrozenState() != 1){
            return;
        }
        int re = frozenRepository.updateFrozenStateById(frozenId,(byte)0);
        if(re > 0){
            pocketRepository.backFrozenDiamondByUserId(userFrozen.getUserId(),userFrozen.getCostNum());
        }
    }

    /**
     * 验证用户剩余钻石
     */
    private double verifySurplusDiamond(int userId,double costNum){
        double surplusDiamond = pocketRepository.findSurplusDiamondById(userId);
        if(surplusDiamond < costNum){
            throw new UserRemindException(UserResultEnum.NOT_MONEY);
        }
        return surplusDiamond;
    }

    /**
     * 计算收益
     * @return 收益值
     */
    private double calculateIncome(double costNum,double transRate,byte roundWay){
        if(costNum <= 0 || transRate == 0){
            return 0;
        }
        //转换值为1直接返回花费
        if(transRate == 1){
            return costNum;
        }
        double incomeNum;
        switch (roundWay){
            case 1:
                incomeNum = Math.floor(costNum * transRate);
                break;
            case 2:
                incomeNum = Math.rint(costNum * transRate);
                break;
            case 3:
                incomeNum = Math.ceil(costNum * transRate);
                break;
            default:
                incomeNum = costNum * transRate;
                break;
        }
        return incomeNum;
    }

    private UserIncomeType getIncomeType(String incomeCode){
        UserIncomeType userIncomeType = UserResourceUtil.IncomeTypeMap.get(incomeCode);
        if(userIncomeType == null){
            List<UserIncomeType> list = incomeTypeRepository.findAll();
            for (UserIncomeType info:list){
                if(StringUtils.equals(info.getIncomeCode(),incomeCode)){
                    userIncomeType = info;
                }
                UserResourceUtil.IncomeTypeMap.put(info.getIncomeCode(),info);
            }
        }
        if(userIncomeType == null){
            throw new UserRemindException(UserResultEnum.INCOME_NOT_EXIST);
        }
        if(userIncomeType.getIsEnable() == BooleanTypeEnum.FALSE.getType()){
            throw new RemindException(BaseResultEnum.FUNCTION_CLOSE);
        }
        return userIncomeType;
    }

    private UserConsumeType getConsumeType(String consumeCode){
        UserConsumeType userConsumeType = userResourceBiz.getConsumeType(consumeCode);
        if(userConsumeType == null){
            throw new UserRemindException(UserResultEnum.CONSUME_NOT_EXIST);
        }
        if(userConsumeType.getIsEnable() == BooleanTypeEnum.FALSE.getType()){
            throw new RemindException(BaseResultEnum.FUNCTION_CLOSE);
        }
        return userConsumeType;
    }

    private UserConsume recordUserConsume(int userId,double costNum,int extendId,double incomeNum,int moduleId,int toId,String consumeCode){
        UserConsume record = new UserConsume();
        record.setUserId(userId);
        record.setCostNum(costNum);
        record.setExtendId(extendId);
        record.setIncomeNum(incomeNum);
        record.setModuleId(moduleId);
        record.setToId(toId);
        record.setConsumeCode(consumeCode);
        record.setAddTime(LibDateUtil.getNowTime());
        return consumeRepository.save(record);
    }


}
