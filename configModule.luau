local EntityConfig = {}

function EntityConfig.getConfig(entityType)
	
local baseConfig  = {
	MaxHealth = 100,
	Health = 100,
	WalkSpeed = 14,
	Damage = 20,
	AttackSpeed = 0.5,
	AggroDistance = 30,
	ThrotleForChase = 5,
	Defense = 5,
	AttackRange = 3,
	CoinDrop = math.random(1,5),
	XPDrop = math.random(1,15),
	Loot = nil
}

local customConfig = {}

if entityType.Name == "Zombie" then
	customConfig = {
		MaxHealth = 120,
		Health = 120,
		MoveSpeed = 13
	}
	
elseif entityType.Name == "EliteZombie" then
	customConfig = {
		MaxHealth = 250,
		Health = 250,
		MoveSpeed=14,
		Damage = 35
	}
	end
	
	local finalConfig = {}
	
	for i,v in baseConfig  do
		finalConfig[i] = v
	end
	
	for i,v in customConfig do
		
		finalConfig[i] = v
	
	end
	
	return finalConfig
	
end

return EntityConfig
