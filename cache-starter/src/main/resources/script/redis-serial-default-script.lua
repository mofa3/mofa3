local date_time_key = redis.call('GET', KEYS[1]);
if date_time_key ~= tostring(ARGV[2]) then
    if date_time_key and (tostring(ARGV[2]) - date_time_key) ~= 0 then
        redis.call('SET', KEYS[1], tostring(ARGV[2]));
        redis.call('SET', KEYS[2], tonumber(ARGV[3]));
    elseif not date_time_key then
        redis.call('SET', KEYS[1], tostring(ARGV[2]));
        redis.call('SET', KEYS[2], tonumber(ARGV[3]));
    end
end
local sequence;
if redis.call('GET', KEYS[2]) == nil then
    sequence = tonumber(ARGV[3]);
    redis.call('SET', KEYS[2], tonumber(ARGV[3]));
else
    sequence = tonumber(redis.call('INCRBY', KEYS[2], 1));
end
return tostring(ARGV[1]) .. tostring(ARGV[2]) .. string.format(tostring(ARGV[4]), sequence);
