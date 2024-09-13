local SpawnMobFunction = {}


function SpawnMobFunction.SpawnEntityFunction(entityData,entityList)
	local EntityStats = require(game.ServerScriptService.EntityScripts.EntityStats)
	local EntityConfig = EntityStats.getConfig(entityData.entityType)
	local RepStorage = game:GetService("ReplicatedStorage")
	local tag = entityData.entityType.Name
	local clonedEntity
		if RepStorage:FindFirstChild(entityData.entityType.Name) then

			--local randomPosition = RegionUtils.RandomPosInRegion(Areas[1])
			clonedEntity = RepStorage:FindFirstChild(entityData.entityType.Name):Clone()
	
			clonedEntity.Parent = game.Workspace
			clonedEntity:SetPrimaryPartCFrame(CFrame.new(entityData.spawnLocation))
			clonedEntity:AddTag(entityData.entityType.Name)
			entityData.entityCount += 1
			table.insert(entityList,clonedEntity)
			clonedEntity:AddTag(tag)
			for i, v in EntityConfig do
				if i == "MaxHealth" or i == "Health" or i == "WalkSpeed" then
					if clonedEntity:FindFirstChild("Humanoid") then
						clonedEntity.Humanoid[i] = v		
					end

				else
					clonedEntity:SetAttribute(i, v)
				end
			end
		else
			return
		end
		return clonedEntity
end



return SpawnMobFunction
