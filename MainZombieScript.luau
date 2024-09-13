task.wait(0.5)
local EntityStatsScript = require(game:GetService("ServerScriptService").EntityScripts.EntityStats)
local RepStorage = game:GetService("ReplicatedStorage")
local RegionUtils = require(game:GetService("ServerScriptService").EntityScripts.RegionUtils)
local SpawnMobs = require(game:GetService("ServerScriptService").EntityScripts.SpawnMobs)
local RunService= game:GetService("RunService")
local Dead = require(game:GetService("ServerScriptService").EntityScripts.Dead)
local FindTarget = require(game:GetService("ServerScriptService").EntityScripts.FindTarget)
local AttackModule = require(game:GetService("ServerScriptService").EntityScripts.Attack)
local RoamModule = require(game:GetService("ServerScriptService").EntityScripts.Roam)
local StopChaseModule = require(game:GetService("ServerScriptService").EntityScripts.StopChase)

local Areas = RegionUtils.Areas
local ZombieAttackAnimation = script.ZombieAttackAnimation
	
local entityData ={
	entityType = RepStorage:FindFirstChild("Zombie"),
	 entityCount = 0,
	spawnRate = 5,
	ZombieArea = Areas[1],
	maxEntities = 15,
	spawnLocation = RegionUtils.RandomPosInRegion(Areas[1])
}

local timeSpawned = os.clock()-10 
local entityList = {}
local entityRemoveList = {}
local shouldCleanUp = false
local throttleBehaviour = 0.2
local throttleBehaviourTimer = os.clock()-2


local function areTheyDead(entityData,entityList,entityRemoveList,newEntity)
	Dead.onEntityDeath(entityData,entityList,entityRemoveList,newEntity)
	shouldCleanUp = true
	return shouldCleanUp
end

local function trySpawning()
	if os.clock() - timeSpawned >= entityData.spawnRate and entityData.entityCount < entityData.maxEntities then
		entityData.spawnLocation = RegionUtils.RandomPosInRegion(entityData.ZombieArea)
		local newEntity = SpawnMobs.SpawnEntityFunction(entityData, entityList)
		timeSpawned = os.clock()
		local humanoid = newEntity:FindFirstChildOfClass("Humanoid")
		if humanoid then
			humanoid.Parent:SetAttribute("counterForMoving",0)
			humanoid.HealthChanged:Connect(function()
				if humanoid.Health <= 0 then
					shouldCleanUp = areTheyDead(entityData, entityList, entityRemoveList, newEntity)
				end
			end)
		end
	end
end

local function Attack(entity,AttackAnimationTrack,target)
	AttackModule.CloseRangeAttack(entity,AttackAnimationTrack,target)
end

local function cleanUp()
	for i = #entityRemoveList, 1, -1 do
		local index = table.find(entityList, entityRemoveList[i])
		if index then
			table.remove(entityList, index)
		end
		table.remove(entityRemoveList, i)
	end
end

local function FindTargetFunction(entityRootPart)
	local target = FindTarget.findTarget(entityRootPart) 
	return target
end

local function wrapStopChase(entityRootPart,distance,target)
	StopChaseModule.StartChaseTimer(entityRootPart.Parent,distance,target)
end

local function MoveRandomly(entityRootPart,entityHumanoid)
	RoamModule.RoamRandomly(entityRootPart,entityHumanoid)
end

local function connectBehaviour(entityRootPart,entityHumanoid,AttackAnimationTrack)
	if entityRootPart and entityHumanoid then
		local target = FindTargetFunction(entityRootPart)
		if entityRootPart.Parent:GetAttribute("throttleForChangingAggroDistance") then
			if os.clock() - entityRootPart.Parent:GetAttribute("throttleForChangingAggroDistance") > (entityRootPart.Parent:GetAttribute("ThrotleForChase")/3) and entityRootPart.Parent:GetAttribute("justChangedAggroDistnace") == true then
				entityRootPart.Parent:SetAttribute("AggroDistance",30)
				entityRootPart.Parent:SetAttribute("justChangedAggroDistnace",false)
			end
		end
		local aggroDistance = entityRootPart.Parent:GetAttribute("AggroDistance")
		if target then
			local distance = (entityRootPart.Position - target.Position).magnitude
			entityHumanoid:MoveTo(target.Position)
			if entityRootPart.Position.Y > 7 then
				local randomLocation = RegionUtils.RandomPosInRegion(Areas[1])
				entityRootPart.Parent:SetPrimaryPartCFrame(CFrame.new(randomLocation))
			end
			if distance < 3 and not entityRootPart.Parent:FindFirstChild("Attacked")  then
				Attack(entityRootPart.Parent,AttackAnimationTrack,target)

			elseif distance > 3 and not entityRootPart.Parent:FindFirstChild("Attacked") and target then
				wrapStopChase(entityRootPart,distance,target)
				
			end
		end
	else
		return
	end
end


	

local function getHumanoidAndGetBehaviour()
	local entityRootPart 
	local  entityHumanoid
	local AttackAnimationTrack
	
	
	for i,v in entityList do
if #entityList == 0 then
			return
end

		  entityRootPart = v:WaitForChild("HumanoidRootPart",0.5)--,wait())
		  entityHumanoid = v:WaitForChild("Humanoid",0.5)--,wait())
	if not entityRootPart then
			continue
		end
		
		
		if entityHumanoid then
			AttackAnimationTrack = entityHumanoid:LoadAnimation(ZombieAttackAnimation)
		end
	

		MoveRandomly(entityRootPart,entityHumanoid)
		connectBehaviour(entityRootPart,entityHumanoid,AttackAnimationTrack)
		 
	end
end







	RunService.Heartbeat:Connect(function()
		trySpawning()
		if os.clock() - throttleBehaviourTimer > throttleBehaviour and #entityList > 0 then
		getHumanoidAndGetBehaviour()
		throttleBehaviourTimer = os.clock()		
		end

	if shouldCleanUp then
		cleanUp()
		shouldCleanUp = false
	end
end)
