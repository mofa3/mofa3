local biz_prefix = tostring(ARGV[1]);
local date_time = tostring(ARGV[2]);
local start_index = tonumber(ARGV[3]);
local len= tostring(ARGV[4]);
local date_time_key = redis.call('GET', KEYS[1] );
if date_time_key ~= date_time then
    if date_time_key and (date_time - date_time_key) ~= 0 then
        redis.call('SET', KEYS[1] , date_time);
        redis.call('SET', KEYS[2] , start_index);
    elseif not date_time_key then
        redis.call('SET', KEYS[1] , date_time);
        redis.call('SET', KEYS[2] , start_index);
    end
end
local sequence;
if redis.call('GET', KEYS[2] ) == nil then
    sequence = start_index;
    redis.call('SET', KEYS[2] , start_index);
else
    sequence = tonumber(redis.call('INCRBY', KEYS[2] , 1));
end
return biz_prefix .. date_time .. string.format(len, sequence);




-- 调试
-- ./redis-cli -h 111.xxx.0.xxx  -c -p 6380 --ldb --eval
-- /Users/mc/backupSpaces/mofa3/cache-starter/src/test/resources/script/serial.lua
-- {CACHE_101_5}_DATETIME {CACHE_101_5}_SEQ , 101 200326 1 %05d

-- ./redis-cli -h 111.xxx.0.xxx -c -p 6380 --eval /Users/mc/backupSpaces/mofa3/cache-starter/src/test/resources/script/serial.lua {CACHE_101_5}_DATETIME {CACHE_101_5}_SEQ , 101 200326 1 %05d