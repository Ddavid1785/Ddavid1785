local ReplicatedStorage = game:GetService("ReplicatedStorage")
local StarterGui = game:GetService("StarterGui")
local BoughtToolEvent = ReplicatedStorage:WaitForChild("BoughtToolEvent")
local toolsInShopFolder = game.Workspace.ToolsInShop
local SyncToolTables = ReplicatedStorage:WaitForChild("SyncToolTables")
local toolConfig = require(ReplicatedStorage.ToolConfig)


local tableOfBoughtWeapons = {}

game:GetService("Players").PlayerAdded:Connect(function(player)
 tableOfBoughtWeapons[player] = {}
		player.ChildAdded:Connect(function(child)
			if child:IsA("Tool") then
			if not table.find(tableOfBoughtWeapons[player],child.Name) then
				player:Kick("You do not own this weapon, this is a warning")
			end
			end
		end)
end)


local function buyWeapon(coins,cost,toolBought,player)
	if coins.Value > tonumber(cost) then
		coins.Value -= cost
		local weapon = toolsInShopFolder:FindFirstChild(toolBought)
		table.insert(tableOfBoughtWeapons[player],toolBought)
		if weapon then
			local weaponClone = weapon:Clone() 
			weaponClone.Parent = player.Character  
			player.Character:FindFirstChild("Humanoid"):EquipTool(weaponClone)
			SyncToolTables:FireClient(player,toolBought)
		end
	end
end

local function doesPlayerOwnWeapon(toolBought,player)
	if table.find(tableOfBoughtWeapons[player],toolBought) then
		local weapon = toolsInShopFolder:FindFirstChild(toolBought)
		local weaponClone = weapon:Clone()
		weaponClone.Parent = player.Character  
		player.Character:FindFirstChild("Humanoid"):EquipTool(weaponClone)
		return
	end
end

BoughtToolEvent.OnServerEvent:Connect(function(player,toolBought)
	local leaderstats = player:WaitForChild("leaderstats")
	local coins = leaderstats.Coins
	local config = toolConfig.getConfig(toolBought)
	local cost = config.Cost
doesPlayerOwnWeapon(toolBought,player)
buyWeapon(coins,cost,toolBought,player)

end)

