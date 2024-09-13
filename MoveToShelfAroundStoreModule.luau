local MoveTable = {}
local CollectionService = game:GetService("CollectionService")
local PathfindingService = game:GetService("PathfindingService")
local GroceryListModule = require(game:GetService("ServerScriptService").GetShoppingList)
local SimplePath = require(game:GetService("ReplicatedStorage").SimplePath)
local currentWaypointIndex

--Dictionaries
local waypoints = {}
local currentWaypointIndex = {}
local blockedConnection = {}
local nextWaypointIndex = {}
local reachedConnection = {}

--Old function very bad over complicated ignore it
--function MoveTable.followPath(destination,character,takeItemAnimationTrack,ShoppingLists,itemName)
--	local path = PathfindingService:CreatePath({
--		AgentRadius = 2,
--		AgentHeight = 5,
--		AgentCanJump = false,
--		AgentCanClimb = false,
--		WaypointSpacing = 6,
--		Costs = {
--			ForceFeild = math.huge
--		}
--	})
--	local humanoid = character:FindFirstChild("Humanoid")
--	local success, errorMessage = pcall(function()
--		print(ShoppingLists[character.Name])
--		path:ComputeAsync(character:FindFirstChild("HumanoidRootPart").Position, destination)
--	end)

--	if success and path.Status == Enum.PathStatus.Success then
--		waypoints[character.Name] = path:GetWaypoints()
--		nextWaypointIndex[character.Name] = 1 
--		character:AddTag("MovingToShelf")
--		if waypoints[character.Name] ~= nil and nextWaypointIndex[character.Name] ~= nil then
--			humanoid:MoveTo(waypoints[character.Name][nextWaypointIndex[character.Name]].Position)
--		else
--			print("no waypoints"..character.Name)
--			return
--		end
--		reachedConnection[character.Name] = humanoid.MoveToFinished:Connect(function(reached)
--			if not waypoints[character.Name] then
--				return
--			end
--			if reached and nextWaypointIndex[character.Name] < #waypoints[character.Name] then
--				nextWaypointIndex[character.Name] += 1
--				humanoid:MoveTo(waypoints[character.Name][nextWaypointIndex[character.Name]].Position)
--			else
--				if   (character:FindFirstChild("HumanoidRootPart").Position-destination).Magnitude > 1 then
--					if (character:GetAttribute("RestartedFunction") == nil) or 
--						((os.clock() - character:GetAttribute("RestartedFunction")) > 5) then
--						character:SetAttribute("RestartedFunction", os.clock())
--						character:RemoveTag("MovingToShelf")
--						MoveTable.followPath(destination, character, takeItemAnimationTrack, ShoppingLists, itemName)
--						return
--					end
--				end
--				reachedConnection[character.Name]:Disconnect()
--				blockedConnection[character.Name] = nil
--				waypoints[character.Name] = nil
--				nextWaypointIndex[character.Name] = nil
--				task.delay(2,function()
--					print(takeItemAnimationTrack)
--					if takeItemAnimationTrack then
--						takeItemAnimationTrack:Play()
--					else
--						warn("Animation track is nil!")
--					end	
--					character:RemoveTag("MovingToShelf")
--					ShoppingLists[character.Name][itemName] = 0
--				end)
--			end
--		end)

--		blockedConnection[character.Name] = path.Blocked:Connect(function(blockedWaypointIndex)
--			if blockedWaypointIndex <= nextWaypointIndex[character.Name] then
--				blockedConnection[character.Name]:Disconnect()
--				reachedConnection[character.Name] = nil
--				blockedConnection[character.Name] = nil
--				waypoints[character.Name] = nil
--				nextWaypointIndex[character.Name] = nil
--				MoveTable.followPath(destination,character,takeItemAnimationTrack,ShoppingLists,itemName)
--			end
--		end)
--	else
--		warn("Pathfinding error: " .. tostring(errorMessage))
--		warn("Path status: ", path.Status)

--	end
--end


--Calculation function used in main script
function MoveTable.CalculateWhereToGo(v,ShoppingLists,folderOfProductsAvaliable)
	for itemName, itemCount in ShoppingLists[v.Name] do
		if itemCount > 0 then
			local productShelf = CollectionService:GetTagged(itemName)[1]  
			if productShelf then
				local targetPos = productShelf:GetAttribute("PositionInFrontOfShelf") 
				--print(itemName)
				--print(ShoppingLists[v.Name])
				return targetPos,itemName
        	end	
		end
	end

end

--used in the main script aswell it just returns true/false depending if everything on the shopping list is > 0
function MoveTable.allPickedUpOrNot(ShoppingLists,v,itemName)
	local allPickedUp = true
	for product, count in ShoppingLists[v.Name] do
		if count > 0 then
			allPickedUp = false
			break
		end
	end
	if allPickedUp == true then
		ShoppingLists[v.Name] = nil
		v:SetAttribute("CurrentState","WalkingToRegister")
		print(v.Name.."Done shopping.")
	end
	return allPickedUp
end

--this function is called once and only once per each customer beacuse they dont need more than 1 path, avoids lag and memory leaks
function MoveTable.CreatePath(character,agentParameters)
	local Path = SimplePath.new(character,agentParameters)
	Path.Visualize = false
	
return Path
end

--refractored move function very simple very good only problem here is Path.Error spits out alot of limit reached and computation errors and the function works better if its not here
function MoveTable.followPathSimple(destination,character,Path,attributeChangeOnStart,attributeChangeWhenReached)
	
	local errorCount = 0
	local maxErrors = 10
	
	--Sets attribute when they start moving

	if attributeChangeOnStart then
		character:SetAttribute("CurrentState",attributeChangeOnStart)
	end
		--if path is blocked compute another path 
	Path.Blocked:Connect(function()
	Path:Run(destination)
	print("blocked")
	end)
	
	Path.Error:Connect(function()
		errorCount = errorCount + 1
		if errorCount <= maxErrors then
			print("Pathfinding error:", character.Name)
			--character:SetAttribute("CurrentState","BrowsingStore")
			--return
		end
		if errorCount == maxErrors then
			warn("Max error limit reached, suppressing further errors.")
		end
	end)
	
	--Path.Error:Connect(function(errorType)
	--	print(character.Name .. " encountered error: " .. tostring(errorType))
	--	if retries < 3 then
	--		if tagRemoveWhenReached then
	--			character:RemoveTag(tagRemoveWhenReached)
	--		end
	--		retries += 1
	--		print(character.Name .. " retrying path...")
	--		Path:Run(destination)
	--	else
	--		warn(character.Name .. " failed to reach destination after retries. Forcing move.")
	--		character:RemoveTag("MovingToShelf")
	--	end
	--end)
	
	--Compute path for the 1st time
	Path:Run(destination)
	
	--Path.Reached:Connect(function()
	--	if tagAddWhenReached then
	--		character:AddTag(tagAddWhenReached)
	--	end
	--	if tagRemoveWhenReached then
	--		print("removed tag:"..tagRemoveWhenReached)
	--		character:RemoveTag(tagRemoveWhenReached)
	--	end
	--end)
	
	--freezes thread untill the customer reaches destination
	Path.Reached:Wait()
	--changes attribute when customer gets there
	if attributeChangeWhenReached then
		character:SetAttribute("CurrentState",attributeChangeWhenReached)
	end

	
return
end


return MoveTable
