local SpawnModule = require(game:GetService("ServerScriptService").SpawnNPCModule)
local MoveToShelfModule = require(game:GetService("ServerScriptService").MoveToShelfsAroundStore)
local RunService = game:GetService("RunService")
local spawnLocations = game.Workspace.Area1.SpawnLocationsForCustomersArea1
local folderOfModels = game:GetService("ReplicatedStorage").CustomerAvatarsArea1
local CollectionService = game:GetService("CollectionService")
local folderOfProductsAvaliable = game:GetService("ReplicatedStorage"):WaitForChild("ProductFolderArea1")
local PathfindingService = game:GetService("PathfindingService")
local CustomerList = {}
local CustomerRemoveList = {}
local ShoppingLists = {}
local Paths = {}
local Threads = {}
local AnimationsForCustomers = {}
local AnimationsTracks = {}

wait(1)

AnimationsForCustomers = game:GetService("ReplicatedStorage").Animations.AnimationsForCustomers:GetChildren()

-- Customer Info
local CustomerInfo = {
	IsCustomer = true,
	Area = "Area1",
	CustomerCount = 0,
	CustomerArea = 1,
	FolderName = "CustomersInGameArea1",
	NormalSpawnRate = 10,
	FasterSpawnRate = 6,
	MaxSpawnRate = 2.5,
	MinimumCustomers = 25,
	MiddleAmountOfCustomers = 35,
	MaxCustomers = 10,
	CustomerCounter = 1
}

local timeSpawned = os.clock()-10
local spawnRate = CustomerInfo.NormalSpawnRate
local thorttleTimer = os.clock()-2
local thorttleTime = 0.2

--COROUTINE CHACHING NOT USING RIGHT NOW IGNORE

--local coroutinePool = {}
--local maxCoroutines = 10
--local availableCoroutines = {}

--local function createWorkerCoroutine()
--	local co = coroutine.create(function()
--		while true do
--			local taskFunction = coroutine.yield()
--			print("Coroutine running task...")

--			if taskFunction then
--				taskFunction()  
--			else
--				print("No task function provided!")
--			end
--			table.insert(availableCoroutines, coroutine.running())
--			print("Task complete, coroutine back to pool")
--		end
--	end)
--	return co
--end

--for i = 1, maxCoroutines do
--	local worker = createWorkerCoroutine()
--	table.insert(availableCoroutines, worker)
--end

--local function assignTaskToCoroutine(taskFunction)
--	if #availableCoroutines > 0 then
--		local coroutineToUse = table.remove(availableCoroutines, 1)
--		print("Assigning task to coroutine, available coroutines left: " .. #availableCoroutines)

--		local success, errorMsg = coroutine.resume(coroutineToUse, taskFunction)
--		coroutine.resume(coroutineToUse, taskFunction)
--		if not success then
--			warn("Coroutine resume failed: " .. errorMsg)
--		end
--	else
--		warn("No available coroutines in the pool")
--	end
--end

--Wrapper to spawn the customers works perfectly as intended
local function spawnCustomer(CustomerInfo,folderOfModels,spawnLocations,CustomerList,Paths,ShoppingLists,folderOfProductsAvaliable,AnimationsForCustomers,AnimationsTracks)
	if CustomerInfo.CustomerCount < CustomerInfo.MinimumCustomers then
		spawnRate = CustomerInfo.MaxSpawnRate
	elseif CustomerInfo.CustomerCount < CustomerInfo.MiddleAmountOfCustomers then
		spawnRate = CustomerInfo.FasterSpawnRate 
	else
		spawnRate = CustomerInfo.NormalSpawnRate 
	end
	if os.clock() - timeSpawned >= spawnRate and CustomerInfo.CustomerCount < CustomerInfo.MaxCustomers then
		timeSpawned = os.clock()
		SpawnModule.SpawnNPCFunction(CustomerInfo,folderOfModels,spawnLocations,CustomerList,Paths,ShoppingLists,folderOfProductsAvaliable,AnimationsForCustomers,AnimationsTracks)
		CustomerInfo.CustomerCounter+=1
		if CustomerInfo.CustomerCounter == 100 then
			CustomerInfo.CustomerCounter = 1
		end
	end
end

--Wrapper for a calculation function (not necessary but Im using it)
local function getVariables(v)
	local targetPos,itemName
	targetPos,itemName = MoveToShelfModule.CalculateWhereToGo(v, ShoppingLists, folderOfProductsAvaliable)
	return targetPos,itemName
end

--uses the moving function but also adds aditional behaviour 
local function shelfBehaviour(targetPos,v,itemName)
	v:SetAttribute("CurrentState","MovingToShelf")
	--print("set movingToStore")
	MoveToShelfModule.followPathSimple(targetPos,v,Paths[v.Name],nil,nil)
	ShoppingLists[v.Name][itemName] = 0
	local allPickedUp = MoveToShelfModule.allPickedUpOrNot(ShoppingLists, v)
	 if allPickedUp == true then
			return
	 end
	--print("set browseStore")
	v:SetAttribute("CurrentState","BrowsingStore")
end

--wrapper for the function above it just spawns a thread for the customer in Theards[customer.Name] additionall functionality can be added but is preffered to be kept in the function above
local function spawnAndHandleTask(v, targetPos, itemName, allPickedUp)
	Threads[v.Name] = task.spawn(function()
		shelfBehaviour(targetPos, v, itemName, allPickedUp)
		--v:SetAttribute("CurrentState","BrowsingStore")
		--print("shelf behaviour done")
	end)
end

--function utilizing most other functions made 10 times more simple than before, this function is what does the work after the customers spawn
local function customerBehaviour()
	for i, v in CollectionService:GetTagged("Customer in Area:1") do
		local state = v:GetAttribute("CurrentState")
		if state == "Spawned" then
			MoveToShelfModule.followPathSimple(game.Workspace.GuidePartInFrontOfStore.Position,v,Paths[v.Name],"WalkingToStore","BrowsingStore")
		elseif state == "BrowsingStore" then
			local targetPos,itemName = getVariables(v)
			spawnAndHandleTask(v, targetPos, itemName)
			
		elseif state == "WalkingToRegister" then
			
		end

		--if v:HasTag("PickedAllItems") then
			
		--elseif v:HasTag("BrowsingStore") then
		--	if  v:HasTag("MovingToShelf") then
		--		continue
		--	end
		
		--	local targetPos,itemName = getVariables(v)
		--	local allPickedUp = false

		--	spawnAndHandleTask(v, targetPos, itemName, allPickedUp)

		--elseif v:HasTag("JustSpawned") then
		--	v:RemoveTag("JustSpawned")
		--	MoveToShelfModule.followPathSimple(game.Workspace.GuidePartInFrontOfStore.Position,v,Paths[v.Name],nil,nil,"BrowsingStore")
	
		--end
	end
end
	
--Main Loop runs 60 times per second but with a throttle is kept to only about 5 times a second beacuse it is unaccesary and helps with removing lag
RunService.Heartbeat:Connect(function()
	if os.clock() - thorttleTimer > thorttleTime then
		thorttleTimer = os.clock()
		spawnCustomer(CustomerInfo,folderOfModels,spawnLocations,CustomerList,Paths,ShoppingLists,folderOfProductsAvaliable,AnimationsForCustomers,AnimationsTracks)
		customerBehaviour()
	end
end)
