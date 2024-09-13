local ReplicatedStorage = game:GetService("ReplicatedStorage")
local ServerScriptService = game:GetService("ServerScriptService")
local StarterGui = game:GetService("StarterGui")

local ToolImages = require(ServerScriptService.Tools.ToolImages)
local ToolConfig = require(ReplicatedStorage.ToolConfig)

local toolsInShopFolder = workspace:FindFirstChild("ToolsInShop")

local order = 1
local buttonTable = {}
local sortedTools = {}

local function InsertTools()
	for i, v in toolsInShopFolder:GetChildren() do
		local ToolConfigTable = ToolConfig.getConfig(v.Name)
		local orderInShop = ToolConfigTable.OrderInShop

		table.insert(sortedTools, {order = orderInShop, tool = v})
	end
end

local function SortTools()
	table.sort(sortedTools, function(a, b)
		return a.order < b.order
	end)
end

InsertTools()
SortTools()

local function CreateToolUI()
	for _, value in sortedTools do
		local ToolConfigTable = ToolConfig.getConfig(value.tool.Name)
		local cost = ToolConfigTable.Cost
		local key = value.tool.Name
		local buttonForWeapon = ReplicatedStorage.ButtonTemplates.TemplateForWeaponShop:Clone()
		--local stringForCost = ""
		--if cost<100 then
		--	stringForCost = cost
		--elseif cost > 99 then
		--	stringForCost =  cost.."  "
		--elseif cost >  1000 then
		--	cost/=1000
		--	stringForCost = cost.."K"
		--end
		buttonForWeapon.Image = ToolImages[key]
		buttonForWeapon.CostInShop.Text = cost
		buttonForWeapon.Parent = StarterGui.WeaponGui.Frame.FrameForWeapons.ScrollingFrame
		buttonForWeapon.Name = key.." Button"

		table.insert(buttonTable, buttonForWeapon)
	end
end

CreateToolUI()

StarterGui.WeaponGui.Frame.DescriptionOfWeaponFrame:SetAttribute("ButtonsReady",true)

