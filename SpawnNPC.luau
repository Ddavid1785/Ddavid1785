local tableOfSpawnFunctions = {}
local MoveToShelfModule = require(game:GetService("ServerScriptService").MoveToShelfsAroundStore)
local GroceryListModule = require(game:GetService("ServerScriptService").GetShoppingList)

function tableOfSpawnFunctions.SpawnNPCFunction(InfoTable,folderOfNPCS,SpawnLocations,ListOfNPCS,Paths,ShoppingLists,folderOfProductsAvaliable,AnimationsForCustomers,AnimationsTracks) 
	local RS = game:GetService("ReplicatedStorage")
	
		if InfoTable.IsCustomer == true then
			if InfoTable.CustomerCount < InfoTable.MaxCustomers then
			local SpawnLocation = SpawnLocations:GetChildren()[math.random(1,#SpawnLocations:GetChildren())]
			local RandomCustomer = folderOfNPCS:GetChildren()[math.random(1,#folderOfNPCS:GetChildren())]:Clone()	
				RandomCustomer.Parent = game.Workspace[InfoTable.Area][InfoTable.FolderName]
				RandomCustomer.Name = "Customer"..InfoTable.CustomerCounter
			local agentParameters = {
				AgentRadius = 2,
				AgentHeight = 5,
				AgentCanJump = true,
				WaypointSpacing = 4,
				Costs = {
					RoofShingles = 0.2,
					Sand = 1,
					Concrete = 11,
					ForceField = math.huge
				}
			}
			
			AnimationsTracks[RandomCustomer.Name] = {}
			for i, v in AnimationsForCustomers do
				local AnimationTrack = RandomCustomer.Humanoid:LoadAnimation(v)
				AnimationsTracks[RandomCustomer.Name][v.Name] = AnimationTrack
			end
			Paths[RandomCustomer.Name] = MoveToShelfModule.CreatePath(RandomCustomer,agentParameters)
			ShoppingLists[RandomCustomer.Name] = GroceryListModule.CreateShoppingList(folderOfProductsAvaliable)
				RandomCustomer:FindFirstChild("HumanoidRootPart").CFrame = SpawnLocation.CFrame
				RandomCustomer:AddTag("Customer in Area:"..InfoTable.CustomerArea)
				RandomCustomer:SetAttribute("CurrentState","Spawned")
				InfoTable.CustomerCount+=1
				table.insert(ListOfNPCS,RandomCustomer)
			else return
			end
		else
			if InfoTable.RobberCount < InfoTable.MaxRobbers then
			local SpawnLocation = SpawnLocations:GetChildren()[math.random(1,#SpawnLocations:GetChildren())]
			local RandomCustomer = folderOfNPCS:GetChildren()[math.random(1,#folderOfNPCS:GetChildren())]:Clone()	
			
			RandomCustomer.Parent = game.Workspace[InfoTable.FolderName]
			RandomCustomer.Name = "Robber"

			RandomCustomer:FindFirstChild("HumanoidRootPart").CFrame = SpawnLocation.CFrame
			RandomCustomer:AddTag("Robber")
			InfoTable.RobberCount+=1
			end
	end
end
return tableOfSpawnFunctions
