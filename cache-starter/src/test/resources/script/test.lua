local goodsSurplus
local flag
-- 判断用户是否已抢过
local buyMembersKey = tostring(KEYS[1])
local memberUid = tonumber(ARGV[1])
local goodsSurplusKey = tostring(KEYS[2])
local hasBuy = redis.call("sIsMember", buyMembersKey, memberUid)

-- 已经抢购过，返回0
if hasBuy ~= 0 then
    return 0
end

-- 准备抢购
goodsSurplus = redis.call("GET", goodsSurplusKey)
if goodsSurplus == false then
    return 0
end

-- 没有剩余可抢购物品
goodsSurplus = tonumber(goodsSurplus)
if goodsSurplus <= 0 then
    return 0
end

flag = redis.call("SADD", buyMembersKey, memberUid)
flag = redis.call("DECR", goodsSurplusKey)

return 1
-- ./redis-cli -h 111.xxx.0.xxx  -c -p 6380
-- 调试
-- redis-cli --ldb --eval
-- 运行
-- redis-cli --eval
-- /Users/mc/backupSpaces/mofa3/cache-starter/src/test/resources/script/test.lua
-- hadBuyUids goodsSurplus , 5824742984